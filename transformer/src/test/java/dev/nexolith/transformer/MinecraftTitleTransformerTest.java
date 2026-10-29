/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class MinecraftTitleTransformerTest {
    @Test
    void appendsNexolithBrandToMinecraftTitle() throws Exception {
        byte[] transformed = MinecraftTitleTransformer.transformTitleClass(fakeMinecraftClass());
        Class<?> minecraftClass = new ByteArrayClassLoader().define(transformed);
        Object instance = minecraftClass.getConstructor().newInstance();
        Method createTitle = minecraftClass.getDeclaredMethod("createTitle");
        createTitle.setAccessible(true);

        assertEquals("Minecraft 26.3 | Nexolith Phase 1", createTitle.invoke(instance));
    }

    @Test
    void ignoresUnrelatedClasses() {
        MinecraftTitleTransformer transformer = new MinecraftTitleTransformer();
        assertNull(transformer.transform(null, null, "example/Other", null, null, new byte[0]));
    }

    @Test
    void failsClosedWhenTheRequiredMinecraftMethodChanges() {
        MinecraftTitleTransformer transformer = new MinecraftTitleTransformer();
        byte[] invalidResult = transformer.transform(
                null, null, MinecraftTitleTransformer.TARGET_CLASS, null, null, classWithoutTitleMethod());

        assertArrayEquals(new byte[] {0}, invalidResult);
        assertEquals("failed", System.getProperty("nexolith.transformer.minecraftTitle"));
    }

    private static byte[] fakeMinecraftClass() {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        writer.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, MinecraftTitleTransformer.TARGET_CLASS,
                null, "java/lang/Object", null);

        MethodVisitor constructor = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();

        MethodVisitor title = writer.visitMethod(
                Opcodes.ACC_PRIVATE, MinecraftTitleTransformer.TARGET_METHOD,
                MinecraftTitleTransformer.TARGET_DESCRIPTOR, null, null);
        title.visitCode();
        title.visitLdcInsn("Minecraft 26.3");
        title.visitInsn(Opcodes.ARETURN);
        title.visitMaxs(0, 0);
        title.visitEnd();

        writer.visitEnd();
        return writer.toByteArray();
    }

    private static byte[] classWithoutTitleMethod() {
        ClassWriter writer = new ClassWriter(0);
        writer.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, MinecraftTitleTransformer.TARGET_CLASS,
                null, "java/lang/Object", null);
        writer.visitEnd();
        return writer.toByteArray();
    }

    private static final class ByteArrayClassLoader extends ClassLoader {
        Class<?> define(byte[] bytes) {
            return defineClass("net.minecraft.client.Minecraft", bytes, 0, bytes.length);
        }
    }
}
