module MeshApp {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;

    opens fr.insa.empire.graphique to javafx.graphics;
    opens fr.insa.empire.syslin to javafx.graphics;
    opens fr.insa.empire.treillis to javafx.graphics;
    opens fr.insa.empire.utils to javafx.graphics, javafx.media;
}