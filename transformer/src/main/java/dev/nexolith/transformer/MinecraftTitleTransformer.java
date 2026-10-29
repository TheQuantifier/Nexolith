/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicBoolean;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Applies the first deliberately narrow Nexolith transformation to Minecraft 26.3. */
final class MinecraftTitleTransformer implements ClassFileTransformer {
    static final String TARGET_CLASS = "net/minecraft/client/Minecraft";
    static final String TARGET_METHOD = "createTitle";
    static final String TARGET_DESCRIPTOR = "()Ljava/lang/String;";
    static final String TITLE_SUFFIX = " | Nexolith Phase 1";

    @Override
    public byte[] transform(
            Module module,
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) {
        if (!TARGET_CLASS.equals(className)) {
            return null;
        }

        try {
            byte[] transformed = transformTitleClass(classfileBuffer);
            System.setProperty("nexolith.transformer.minecraftTitle", "applied");
            System.err.println("[Nexolith] Branded Minecraft window title");
            return transformed;
        } catch (RuntimeException exception) {
            System.setProperty("nexolith.transformer.minecraftTitle", "failed");
            System.err.println("[Nexolith] Required Minecraft title transformation failed: " + exception);
            // Transformer exceptions are ignored by the instrumentation API. Returning an invalid
            // class instead makes a required, version-pinned hook fail closed with ClassFormatError.
            return new byte[] {0};
        }
    }

    static byte[] transformTitleClass(byte[] originalClass) {
        ClassReader reader = new ClassReader(originalClass);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        AtomicBoolean methodFound = new AtomicBoolean();

        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!TARGET_METHOD.equals(name) || !TARGET_DESCRIPTOR.equals(descriptor)) {
                    return delegate;
                }

                methodFound.set(true);
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.ARETURN) {
                            super.visitLdcInsn(TITLE_SUFFIX);
                            super.visitMethodInsn(
                                    Opcodes.INVOKEVIRTUAL,
                                    "java/lang/String",
                                    "concat",
                                    "(Ljava/lang/String;)Ljava/lang/String;",
                                    false);
                        }
                        super.visitInsn(opcode);
                    }
                };
            }
        };

        reader.accept(visitor, 0);
        if (!methodFound.get()) {
            throw new IllegalStateException("Minecraft 26.3 title method was not found");
        }
        return writer.toByteArray();
    }
}
