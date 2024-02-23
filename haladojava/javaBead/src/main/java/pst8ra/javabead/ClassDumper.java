package pst8ra.javabead;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ClassDumper {
    public static String dump(Class<?> clazz) {
        if (clazz == null || clazz.isPrimitive() || clazz.isArray() || clazz.isEnum() || clazz.isAnnotation() || clazz.isInterface() || clazz.isRecord() || clazz.isAnonymousClass()) {
            return "";
        } else {
            int modifiers = clazz.getModifiers();
            String clazzAccess = Modifier.isPublic(modifiers) ? "public " : Modifier.isProtected(modifiers) ? "protected " : Modifier.isPrivate(modifiers) ? "private " : "";
            String clazzStatic = Modifier.isStatic(modifiers) ? "static " : "";
            String clazzFinal = Modifier.isFinal(modifiers) ? "final " : "";
            String clazzAbstract = Modifier.isAbstract(modifiers) ? "abstract " : "";

            String clazzName = clazz.getSimpleName();

            String clazzSuper = clazz.getSuperclass() != null ? " extends " + clazz.getSuperclass().getName() : "";
            String clazzInterfaces = clazz.getInterfaces().length > 0 ? " implements " + Arrays.stream(clazz.getInterfaces())
                    .map(Class::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .get() : "";

            String fields = Arrays.stream(clazz.getDeclaredFields())
                    .map(f -> {
                        int fieldModifiers = f.getModifiers();
                        String fieldAccess = Modifier.isPublic(fieldModifiers) ? "public " : Modifier.isProtected(fieldModifiers) ? "protected " : Modifier.isPrivate(fieldModifiers) ? "private " : "";
                        String fieldStatic = Modifier.isStatic(fieldModifiers) ? "static " : "";
                        String fieldFinal = Modifier.isFinal(fieldModifiers) ? "final " : "";
                        String fieldTransient = Modifier.isTransient(fieldModifiers) ? "transient " : "";
                        String fieldVolatile = Modifier.isVolatile(fieldModifiers) ? "volatile " : "";
                        String fieldType = f.getType().getName();
                        String fieldName = f.getName();
                        return fieldAccess + fieldStatic + fieldTransient + fieldVolatile +  fieldFinal + fieldType + " " + fieldName + ";";
                    })
                    .reduce((a, b) -> a + "\n\t" + b)
                    .orElse("");

            String methods = Arrays.stream(clazz.getDeclaredMethods())
                    .map(m -> {
                        int methodModifiers = m.getModifiers();
                        String methodAccess = Modifier.isPublic(methodModifiers) ? "public " : Modifier.isProtected(methodModifiers) ? "protected " : Modifier.isPrivate(methodModifiers) ? "private " : "";
                        String methodStatic = Modifier.isStatic(methodModifiers) ? "static " : "";
                        String methodFinal = Modifier.isFinal(methodModifiers) ? "final " : "";
                        String methodSynchronized = Modifier.isSynchronized(methodModifiers) ? "synchronized " : "";
                        String methodNative = Modifier.isNative(methodModifiers) ? "native " : "";
                        String methodAbstract = Modifier.isAbstract(methodModifiers) ? "abstract " : "";
                        String methodReturnType = m.getReturnType().getSimpleName();
                        String methodName = m.getName();

                        return methodAccess + methodStatic + methodFinal + methodSynchronized + methodNative + methodAbstract + methodReturnType + " " + methodName + "(...) { /* method body */ }";
                    })
                    .reduce((a, b) -> a + "\n\t" + b)
                    .orElse("");

            return clazzAccess + clazzStatic + clazzFinal + clazzAbstract + "class " + clazzName + clazzSuper + clazzInterfaces + " {\n\t" + fields + "\n\n\t" +  methods + "\n}";
        }
    }
}
