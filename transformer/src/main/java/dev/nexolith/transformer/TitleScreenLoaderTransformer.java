/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/** Adds a Nexolith identity marker to Minecraft 26.3's bottom-left title-screen line. */
final class TitleScreenLoaderTransformer implements ClassFileTransformer {
    static final String TARGET_CLASS = "net/minecraft/client/gui/screens/TitleScreen";
    static final String TARGET_METHOD = "extractRenderState";
    static final String TARGET_DESCRIPTOR =
            "(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V";
    static final String TEXT_OWNER = "net/minecraft/client/gui/GuiGraphicsExtractor";
    static final String TEXT_METHOD = "text";
    static final String TEXT_DESCRIPTOR =
            "(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V";
    static final String LOADER_LABEL = "Nexolith Loader 0.0.1 (Phase 1)";
    private static final int VERSION_TEXT_LOCAL = 6;

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
            byte[] transformed = transformTitleScreenClass(classfileBuffer);
            System.setProperty("nexolith.transformer.titleScreenLoader", "applied");
            System.err.println("[Nexolith] Added loader identity to the title screen");
            return transformed;
        } catch (RuntimeException exception) {
            System.setProperty("nexolith.transformer.titleScreenLoader", "failed");
            System.err.println("[Nexolith] Required title-screen loader label failed: " + exception);
            return new byte[] {0};
        }
    }

    static byte[] transformTitleScreenClass(byte[] originalClass) {
        ClassNode classNode = new ClassNode(Opcodes.ASM9);
        new ClassReader(originalClass).accept(classNode, 0);

        MethodNode target = classNode.methods.stream()
                .filter(method -> TARGET_METHOD.equals(method.name)
                        && TARGET_DESCRIPTOR.equals(method.desc))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Minecraft 26.3 title-screen render method was not found"));

        MethodInsnNode versionDraw = null;
        VarInsnNode versionStringLoad = null;
        for (AbstractInsnNode instruction = target.instructions.getFirst();
                instruction != null;
                instruction = instruction.getNext()) {
            if (!(instruction instanceof MethodInsnNode call)
                    || call.getOpcode() != Opcodes.INVOKEVIRTUAL
                    || !TEXT_OWNER.equals(call.owner)
                    || !TEXT_METHOD.equals(call.name)
                    || !TEXT_DESCRIPTOR.equals(call.desc)) {
                continue;
            }

            VarInsnNode candidate = findPreviousVersionStringLoad(call);
            if (candidate != null) {
                if (versionDraw != null) {
                    throw new IllegalStateException(
                            "Minecraft 26.3 title-screen version draw was ambiguous");
                }
                versionDraw = call;
                versionStringLoad = candidate;
            }
        }

        if (versionDraw == null || versionStringLoad == null) {
            throw new IllegalStateException(
                    "Minecraft 26.3 title-screen version draw was not found");
        }

        InsnList branding = new InsnList();
        branding.add(new VarInsnNode(Opcodes.ALOAD, 1));
        branding.add(new VarInsnNode(Opcodes.ALOAD, 0));
        branding.add(new FieldInsnNode(
                Opcodes.GETFIELD,
                "net/minecraft/client/gui/screens/Screen",
                "font",
                "Lnet/minecraft/client/gui/Font;"));
        branding.add(new LdcInsnNode(LOADER_LABEL));
        branding.add(new InsnNode(Opcodes.ICONST_2));
        branding.add(new VarInsnNode(Opcodes.ALOAD, 0));
        branding.add(new FieldInsnNode(
                Opcodes.GETFIELD,
                "net/minecraft/client/gui/screens/Screen",
                "height",
                "I"));
        branding.add(new IntInsnNode(Opcodes.BIPUSH, 20));
        branding.add(new InsnNode(Opcodes.ISUB));
        branding.add(new VarInsnNode(Opcodes.FLOAD, 5));
        branding.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "net/minecraft/util/ARGB",
                "white",
                "(F)I",
                false));
        branding.add(new MethodInsnNode(
                Opcodes.INVOKEVIRTUAL,
                TEXT_OWNER,
                TEXT_METHOD,
                TEXT_DESCRIPTOR,
                false));
        target.instructions.insert(versionDraw, branding);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private static VarInsnNode findPreviousVersionStringLoad(MethodInsnNode drawCall) {
        int examined = 0;
        for (AbstractInsnNode instruction = drawCall.getPrevious();
                instruction != null && examined < 16;
                instruction = instruction.getPrevious()) {
            examined++;
            if (instruction instanceof VarInsnNode variable
                    && variable.getOpcode() == Opcodes.ALOAD
                    && variable.var == VERSION_TEXT_LOCAL) {
                return variable;
            }
        }
        return null;
    }
}
