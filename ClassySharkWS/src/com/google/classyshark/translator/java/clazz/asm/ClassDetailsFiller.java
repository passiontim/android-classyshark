/*
 * Copyright 2015 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.classyshark.translator.java.clazz.asm;

import com.google.classyshark.translator.java.MetaObject;
import com.google.classyshark.translator.java.dex.DexlibAdapter;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Type.getArgumentTypes;
import static org.objectweb.asm.Type.getReturnType;

public class ClassDetailsFiller extends ClassVisitor {
    private MetaObject.AnnotationInfo[] annotationInfo = new MetaObject.AnnotationInfo[0];
    private String name = "test";
    private int modifiers = 0;
    private String superClass = "test";
    private String superclassGenerics = "";
    private MetaObject.InterfaceInfo[] interfaces = new MetaObject.InterfaceInfo[0];

    private List<MetaObject.FieldInfo> declaredFields = new ArrayList<>();

    private MetaObject.ConstructorInfo[] declaredConstructors = new MetaObject.ConstructorInfo[0];
    private MetaObject.MethodInfo[] declaredMethods = new MetaObject.MethodInfo[0];

    public ClassDetailsFiller() {
        super(Opcodes.ASM5);
    }

    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");

        this.name = name.replaceAll("/", "\\.");
        this.superClass = superName.replaceAll("/", "\\.");
        this.modifiers = access;
    }

    public void visitSource(String source, String debug) {
    }

    public void visitOuterClass(String owner, String name, String desc) {
    }

    public AnnotationVisitor visitAnnotation(String desc,
                                             boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) {
    }

    public void visitInnerClass(String name, String outerName,
                                String innerName, int access) {
    }

    public FieldVisitor visitField(int access, String name, String desc,
                                   String signature, Object value) {
        System.out.println(" " + desc + " " + name);

        MetaObject.FieldInfo fi = new
                MetaObject.FieldInfo();

        fi.typeName = DexlibAdapter.getTypeName(desc);
        fi.modifiers = access;
        fi.annotations = new MetaObject.AnnotationInfo[0];
        fi.name = name.replaceAll("/", "\\.");

        declaredFields.add(fi);

        return null;
    }

    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {

        // TODO stopped here fill the methods and constructors
        // TODO note the qualified names
        // constuctor's name is <init>
        System.out.println(" " + name + desc);

        org.objectweb.asm.Type[] arguments = getArgumentTypes(desc);
        org.objectweb.asm.Type returnType = getReturnType(desc);

        return null;
    }

    public void visitEnd() {
    }

    public static void main(String[] args) throws Exception {

        final File testFile = new File(System.getProperty("user.home") +
                "/Desktop/Scenarios/3 Class/Reducer.class");

        RandomAccessFile f = new RandomAccessFile(testFile, "r");
        byte[] b = new byte[(int) f.length()];
        f.read(b);

        ClassDetailsFiller cp = new ClassDetailsFiller();
        ClassReader cr = new ClassReader(b);
        cr.accept(cp, 0);
    }

    public String getClassGenerics(String name) {
        return null;
    }

    public String getName() {
        return name;
    }

    public MetaObject.AnnotationInfo[] getAnnotationInfo() {
        return annotationInfo;
    }

    public int getModifiers() {
        return modifiers;
    }

    public String getSuperClass() {
        return superClass;
    }

    public String getSuperclassGenerics() {
        return superclassGenerics;
    }

    public MetaObject.InterfaceInfo[] getInterfaces() {
        return interfaces;
    }

    public MetaObject.FieldInfo[] getDeclaredFields() {
        MetaObject.FieldInfo[] array = new MetaObject.FieldInfo[declaredFields.size()];
        return declaredFields.toArray(array);
    }

    public MetaObject.ConstructorInfo[] getDeclaredConstructors() {
        return declaredConstructors;
    }

    public MetaObject.MethodInfo[] getDeclaredMethods() {
        return declaredMethods;
    }
}