package com;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Stack;

public class Controller {


    public Button posaljiChatPorukuBtn;
    public TextField chatPorukaInputField;
    public ListView chatPorukeListView;
    public TextField ucesniciChataField;
    public Label datotekaStatusSlanjaLbl;
    public Button posaljiNaServerBtn;
    public ListView<String> datotekaSpisakFajlovaList;
    public Button IzabDatotBtn;
    public Button palindromOkBtn;
    public TextField palindromUnosString;
    ObservableList<File> observableListIzbor = FXCollections.observableArrayList();
    private List<File> izabraniFajlovi = null;

    @FXML
    private GridPane tabela;
    @FXML
    private TextArea resenje;

    private TextField[][] polja;
    private String[][] matrica;
    private Stack<String> smerovi;

    private int robotRed, robotKolona;

    @FXML
    public void initialize() {
        polja = new TextField[9][9];
        resenje.setEditable(false);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((i == 0) || (i == 8)) || ((j == 0) || (j == 8))) {
                    polja[i][j] = new TextField("@");
                }
                else {
                    polja[i][j] = new TextField("");
                }
                tabela.add(polja[i][j], j, i);
            }
        }

    }

    @FXML
    public void pronadji() {

        resenje.setText("");
        smerovi = new Stack<>();
        matrica = new String[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrica[i][j] = polja[i][j].getText();
            }
        }

        if (pronadjiRobota(matrica)) {

            boolean resiv = resiLavirint(robotKolona, robotRed);

            if (resiv) {

                String smeroviZaIzlaz = "";

                while (!smerovi.isEmpty()) {
                    smeroviZaIzlaz += smerovi.pop() + " ";
                }

                resenje.setText(smeroviZaIzlaz);
            }
            else {
                resenje.setText("Neresiv lavirint.");
            }
        }
        else {
            resenje.setText("Morate postaviti robota.");
        }
    }

    public boolean pronadjiRobota(String[][] polje) {

        boolean nasao = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (polje[i][j].equals("R")) {
                    robotRed = i;
                    robotKolona = j;
                    nasao = true;
                    break;
                }
            }
            if (nasao) break;
        }

        return nasao;
    }

    public boolean resiLavirint(int x, int y) {

        if (izlazLavirinta(x, y)) {
            return true;
        }
        else if (mrtvaUlica(x, y)) {
            robotRed = x;
            robotKolona = y;
            if (!smerovi.isEmpty()) {
                smerovi.pop();
            }
            return false;
        }
        else {

            matrica[y][x] = ".";
            robotRed = x;
            robotKolona = y;

            if (y - 1 >= 0 && resiLavirint(x, y - 1)) {
                smerovi.push("Gore");
                return true;
            }
            else if (x + 1 <= matrica[0].length && resiLavirint(x + 1, y)) {
                smerovi.push("Desno");
                return true;
            }
            else if (y + 1 < matrica.length && resiLavirint(x, y + 1)) {
                smerovi.push("Dole");
                return true;
            }
            else if (x - 1 >= 0 && resiLavirint(x - 1, y)) {
                smerovi.push("Levo");
                return true;
            }
            else {

                matrica[y][x] = ".";
                robotRed = x;
                robotKolona = y;

                return false;
            }
        }
    }

    public boolean izlazLavirinta(int x, int y) {
        return matrica[y][x].equals("E");
    }

    public boolean mrtvaUlica(int x, int y) {
        return ".@".contains(matrica[y][x]) && (!matrica[y][x].equals(""));
    }

    public void palindromOkBtnKlik(ActionEvent actionEvent) {

        String rec = palindromUnosString.getText();
        if (rec.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Morate uneti neki tekst");
            alert.showAndWait();
            return;
        }
        boolean odgovor = new Palindrom(rec).proveriPalindrom();

        if (odgovor) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Odgovor");
            alert.setHeaderText(null);
            alert.setContentText("Zadati tekst: " + rec.toUpperCase() + " jeste palindrom.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Odgovor");
            alert.setHeaderText(null);
            alert.setContentText("Zadati tekst: " + rec.toUpperCase() + " nije palindrom.");
            alert.showAndWait();

        }


    }

    public void IzabDatotBtnKlik(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        if (izabraniFajlovi != null) {

        }

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"\\src\\com\\klijent"));
        izabraniFajlovi = fileChooser.showOpenMultipleDialog(null);


        if (izabraniFajlovi != null) {


            datotekaSpisakFajlovaList.getItems().clear();

            for (File f : izabraniFajlovi) {

                datotekaSpisakFajlovaList.getItems().add(f.getName());
                System.out.print(f.getName());
            }

            for (String i: datotekaSpisakFajlovaList.getItems()) {
                System.out.println(i);
            }

        }
        if (izabraniFajlovi != null) {
            posaljiNaServerBtn.setDisable(false);

        }


    }

        public void PosaljiNaServerBtnKlik (ActionEvent actionEvent){
            for (File fajl : izabraniFajlovi ) {


                new Klijent1(fajl);

            }
        }

        public void posaljiChatPorukuBtnKlik (ActionEvent actionEvent){


        }
    }


