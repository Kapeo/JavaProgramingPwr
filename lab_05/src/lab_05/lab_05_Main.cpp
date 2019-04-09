// Save as HelloJNI.c
#include <jni.h>
#include <iostream>
#include "lab_05_Main.h"
 using namespace std;
// Implementation of the native method sayHello()
JNIEXPORT void JNICALL Java_lab_105_Main_sayHello(JNIEnv *env, jobject thisObj) {
   cout << "Hello World!\n";
   return;
}

jobject Java_lab_105_Main_multi01(JNIEnv *env, jobject thisObj, jobjectArray vectorA, jobjectArray vectorB) {
	//przyk³adowy obiekt z wektora
	jobject ObjectA = env->GetObjectArrayElement(vectorA, 0);
	jobject ObjectB = env->GetObjectArrayElement(vectorB, 0);

	//pobieranie klasy z Obiektu i metody z Double
	jclass cls = env->GetObjectClass(ObjectA);
	jmethodID doubleValue = env->GetMethodID(cls, "doubleValue", "()D");

	if (doubleValue == NULL)
		return NULL;

	int lengthA = env->GetArrayLength(vectorA);
	int lengthB = env->GetArrayLength(vectorB);

	double *arrayA = new double[lengthA];
	double *arrayB = new double[lengthB];
	double sum = 0.0;

	for (int i = 0; i < lengthA; ++i) {

		ObjectA = env->GetObjectArrayElement(vectorA, i);
		arrayA[i] = env->CallDoubleMethod(ObjectA, doubleValue);
		//cout << arrayA[i] << "\n";
		ObjectB = env->GetObjectArrayElement(vectorB, i);
		arrayB[i] = env->CallDoubleMethod(ObjectB, doubleValue);
		//cout << arrayB[i] << "\n";
		sum += arrayA[i] * arrayB[i];
	}

	//tworzenie obiektu Double do zwrócenia
	jmethodID init = env->GetMethodID(cls, "<init>", "(D)V");
	jobject to_return = env->NewObject(cls, init, sum);

	delete arrayA, arrayB;
	env->DeleteLocalRef(cls);
	env->DeleteLocalRef(ObjectA);
	env->DeleteLocalRef(ObjectB);

	return to_return;
}

jobject Java_lab_105_Main_multi02(JNIEnv *env, jobject thisObj, jobjectArray vectorA) {

	jclass cls_par = env->GetObjectClass(thisObj);
	jfieldID field = env->GetFieldID(cls_par, "b", "[Ljava/lang/Double;");
	if (field == NULL)
		return NULL;

	jobjectArray vectorB = (jobjectArray)env->GetObjectField(thisObj, field);
	
	
	//przyk³adowy obiekt z wektora
	jobject ObjectA = env->GetObjectArrayElement(vectorA, NULL);
	jobject ObjectB = env->GetObjectArrayElement(vectorB, NULL);

	//pobieranie klasy z Objektu i metody z Double
	jclass cls = env->GetObjectClass(ObjectA);
	jmethodID doubleValue = env->GetMethodID(cls, "doubleValue", "()D");

	if (doubleValue == NULL)
		return NULL;

	int lengthA = env->GetArrayLength(vectorA);
	int lengthB = env->GetArrayLength(vectorB);

	double *arrayA = new double[lengthA];
	double *arrayB = new double[lengthB];
	double sum = 0.0;

	for (int i = 0; i < lengthA; ++i) {

		ObjectA = env->GetObjectArrayElement(vectorA, i);
		arrayA[i] = env->CallDoubleMethod(ObjectA, doubleValue);
		//cout << arrayA[i] << "\n";
		ObjectB = env->GetObjectArrayElement(vectorB, i);
		arrayB[i] = env->CallDoubleMethod(ObjectB, doubleValue);
		//cout << arrayB[i] << "\n";
		sum += arrayA[i] * arrayB[i];
	}

	//tworzenie obiektu Double do zwrócenia
	jmethodID init = env->GetMethodID(cls, "<init>", "(D)V");
	jobject to_return = env->NewObject(cls, init, sum);

	delete arrayA, arrayB;
	env->DeleteLocalRef(cls);
	env->DeleteLocalRef(ObjectA);
	env->DeleteLocalRef(ObjectB);

	return to_return;
}