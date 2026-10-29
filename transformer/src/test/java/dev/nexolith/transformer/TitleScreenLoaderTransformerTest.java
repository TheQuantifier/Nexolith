/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.transformer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class TitleScreenLoaderTransformerTest {
    @Test
    void appendsLoaderIdentityToTheBottomLeftVersionString() {
        byte[] transformed = TitleScreenLoaderTransformer.transformTitleScreenClass(fakeTitleScreen());
        AtomicBoolean labelFound = new AtomicBoolean();

        new ClassReader(transformed).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!TitleScreenLoaderTransformer.TARGET_METHOD.equals(name)) {
                    return delegate;
                }
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitLdcInsn(Object value) {
                        if (TitleScreenLoaderTransformer.LOADER_LABEL.equals(value)) {
                            labelFound.set(true);
                        }
                        super.visitLdcInsn(value);
                    }
                };
            }
        }, 0);

        assertTrue(labelFound.get());
    }

    @Test
    void ignoresUnrelatedClasses() {
        TitleScreenLoaderTransformer transformer = new TitleScreenLoaderTransformer();
        assertNull(transformer.transform(null, null, "example/Other", null, null, new byte[0]));
    }

    @Test
    void failsClosedWhenTheRequiredTitleScreenMethodChanges() {
        TitleScreenLoaderTransformer transformer = new TitleScreenLoaderTransformer();
        byte[] invalidResult = transformer.transform(
                null,
                null,
                TitleScreenLoaderTransformer.TARGET_CLASS,
                null,
                null,
                classWithoutRenderMethod());

        assertArrayEquals(new byte[] {0}, invalidResult);
        assertEquals("failed", System.getProperty("nexolith.transformer.titleScreenLoader"));
    }

    private static byte[] fakeTitleScreen() {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        writer.visit(
                Opcodes.V17,
                Opcodes.ACC_PUBLIC,
                TitleScreenLoaderTransformer.TARGET_CLASS,
                null,
                "java/lang/Object",
                null);

        MethodVisitor render = writer.visitMethod(
                Opcodes.ACC_PUBLIC,
                TitleScreenLoaderTransformer.TARGET_METHOD,
                TitleScreenLoaderTransformer.TARGET_DESCRIPTOR,
                null,
                null);
        render.visitCode();
        render.visitLdcInsn("Minecraft 26.3");
        render.visitVarInsn(Opcodes.ASTORE, 6);
        render.visitVarInsn(Opcodes.ALOAD, 1);
        render.visitInsn(Opcodes.ACONST_NULL);
        render.visitVarInsn(Opcodes.ALOAD, 6);
        render.visitInsn(Opcodes.ICONST_2);
        render.visitInsn(Opcodes.ICONST_3);
        render.visitInsn(Opcodes.FCONST_1);
        render.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                "net/minecraft/util/ARGB",
                "white",
                "(F)I",
                false);
        render.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                TitleScreenLoaderTransformer.TEXT_OWNER,
                TitleScreenLoaderTransformer.TEXT_METHOD,
                TitleScreenLoaderTransformer.TEXT_DESCRIPTOR,
                false);
        render.visitInsn(Opcodes.RETURN);
        render.visitMaxs(0, 0);
        render.visitEnd();
        writer.visitEnd();
        return writer.toByteArray();
    }

    private static byte[] classWithoutRenderMethod() {
        ClassWriter writer = new ClassWriter(0);
        writer.visit(
                Opcodes.V17,
                Opcodes.ACC_PUBLIC,
                TitleScreenLoaderTransformer.TARGET_CLASS,
                null,
                "java/lang/Object",
                null);
        writer.visitEnd();
        return writer.toByteArray();
    }
}
