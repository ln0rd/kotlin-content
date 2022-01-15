package funcoes;

import kotlin.collections.CollectionsKt;

import java.util.ArrayList;
import java.util.Collection;

public class CriandoClasseJava {

    public static void main( String[] args ) {
        ArrayList<String> list = CollectionsKt.arrayListOf("Leo", "Bakugou", "All Might", "Sparrow", "Dante");
        System.out.println(SegundoElementoListKt.secondOrNull(list));
    }

}
