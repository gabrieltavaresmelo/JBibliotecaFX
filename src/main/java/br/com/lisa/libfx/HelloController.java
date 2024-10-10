package br.com.lisa.libfx;

import br.com.lisa.libfx.models.Livro;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField tfBuscar;

    @FXML
    private ListView<Livro> listView;

    private List<Livro> lista;

    @FXML
    public void initialize() {
        System.out.println("init");

        Livro livro1 = new Livro();
        livro1.setNome("Java Básico");
        livro1.setValor(49.9);

        Livro livro2 = new Livro();
        livro2.setNome("Java Avançado");
        livro2.setValor(70.9);

        lista = new ArrayList<>();
        lista.add(livro1);
        lista.add(livro2);

        listView.getItems().add(livro1);
        listView.getItems().add(livro2);
    }

    @FXML
    public void onEditoras() {

    }

    @FXML
    public void onAutores() {

    }

    @FXML
    public void onRemove() {
        Alert alert = exibeAlertConfirm("Excluir", "Tem certeza que deseja remover este livro?");
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK){
                int index = listView.getSelectionModel().getSelectedIndex();
                lista.remove(index);
                listView.getItems().remove(index);

                exibeAlertMsg("Excluído", "Livro excluído com sucesso!");
            }
        });
    }

    @FXML
    public void onAdd() {
        Optional<Pair<String, String>> result = exibeAlertInput("Adicionar", "");
        result.ifPresent(livroValor -> {
//            System.out.println("Username=" + livroValor.getKey() + ", Password=" + livroValor.getValue());
            String nome = livroValor.getKey();
            String valor = livroValor.getValue();

            Livro livro = new Livro();
            livro.setNome(nome);
            livro.setValor(Double.parseDouble(valor));

            lista.add(livro);
            listView.getItems().add(livro);

            exibeAlertMsg("Adicionado", "Livro adicionado com sucesso!");
        });
    }

    @FXML
    public void onBuscar() {
        String texto = tfBuscar.getText();

        listView.getItems().clear();

        if(texto.equals("")) {
            for (Livro livro: lista) {
                listView.getItems().add(livro);
            }
        } else {
            for (Livro livro: lista) {
                if(livro.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    listView.getItems().add(livro);
                }
            }
        }
    }

    public Alert exibeAlertMsg(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait().ifPresent((btnType) -> {
        });

        return alert;
    }

    public Alert exibeAlertConfirm(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        return alert;
    }

    public Optional<Pair<String, String>> exibeAlertInput(String titulo, String msg) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.setHeaderText(msg);

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tf1 = new TextField();
        tf1.setPromptText("Livro");
        TextField tf2 = new TextField();
        tf2.setPromptText("Valor");

        grid.add(new Label("Livro:"), 0, 0);
        grid.add(tf1, 1, 0);
        grid.add(new Label("Valor:"), 0, 1);
        grid.add(tf2, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        tf1.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> tf1.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(tf1.getText(), tf2.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}