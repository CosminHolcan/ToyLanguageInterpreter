package sample;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.adts.IList;
import model.adts.IStack;
import model.programState.ProgramState;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RunProgramGUI implements Initializable {

    @FXML
    private TableView<Map.Entry<String, String>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> symTableIDColumn;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> symTableValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, String>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> heapValueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> execStackListView;

    @FXML
    private ListView<Integer> programIDListView;

    @FXML
    private Button oneStepButton;

    @FXML
    private TextField nrPrgStatesTextField;

    private Controller controller;
    private boolean errorPresent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.symTableIDColumn.setCellValueFactory(new PropertyValueFactory<Map.Entry<String, String>, String>("key"));
        this.symTableValueColumn.setCellValueFactory(new PropertyValueFactory<Map.Entry<String, String>, String>("value"));

        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<Map.Entry<Integer, String>, Integer>("key"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<Map.Entry<Integer, String>, String>("value"));
    }

    public void setController(Controller controller) {
        this.controller = controller;
        this.errorPresent = false;
        this.populateProgramStatesID();
        this.populateFileTable();
        this.populateHeapTable();
        this.populateOutput();
        if (this.controller.getRepo().getProgramsList().size() >= 1) {
            this.programIDListView.getSelectionModel().select(0);
            ProgramState first = this.controller.getRepo().getProgramsList().get(0);
            this.populateExecutionStack(first);
            this.populateSymbolsTable(first);
        }
        else
        {
            this.execStackListView.getItems().clear();
            this.symbolTableView.getItems().clear();
        }

    }

    @FXML
    private void oneStep(ActionEvent actionEvent)
    {
        if (this.errorPresent)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error was found and program can't continue !", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (this.controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No selected program from second window !", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (this.controller.getRepo().getProgramsList().size() < 1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Program is finished !", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            boolean v = this.controller.oneStepForAllWrapper();
            this.populateHeapTable();
            this.populateFileTable();
            this.populateOutput();
            if (this.controller.getRepo().getProgramsList().size() >= 1) {
                this.programIDListView.getSelectionModel().select(0);
                ProgramState first = this.controller.getRepo().getProgramsList().get(0);
                this.populateExecutionStack(first);
                this.populateSymbolsTable(first);
            }
            else
            {
                this.execStackListView.getItems().clear();
                this.symbolTableView.getItems().clear();
            }
            if (v == false)
                this.controller.getRepo().setProgramsList(new ArrayList<>());
            this.populateProgramStatesID();
        }
        catch (InterruptedException | MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            this.errorPresent = true;
            return;
        }

    }


    @FXML
    private void selectProgramState(MouseEvent event)
    {
        if (this.programIDListView.getSelectionModel().getSelectedIndex() < 0)
            return;

        int selectedID = this.programIDListView.getSelectionModel().getSelectedItem();
        ProgramState prg = this.controller.getRepo().getProgramStateByID(selectedID);
        if (prg == null)
            return;
        populateSymbolsTable(prg);
        populateExecutionStack(prg);
    }

    private void populateProgramStatesID()
    {
        List<ProgramState> prgStates = this.controller.getRepo().getProgramsList();
        List<Integer> prgStatesID = prgStates.stream().map(ProgramState::getMyID).collect(Collectors.toList());
        this.programIDListView.setItems(FXCollections.observableList(prgStatesID));
        this.nrPrgStatesTextField.setText(""+prgStatesID.size());
        this.programIDListView.refresh();
    }


    private void populateSymbolsTable(ProgramState selectedProgramState)
    {
        IDictionary<String, IValue> symTable = selectedProgramState.getSymbolsTable();
        List<Map.Entry<String, String>> symTableList = new ArrayList<>();
        for (var id : symTable.getKeys())
        {
            Map.Entry<String, String> e = new AbstractMap.SimpleEntry<>(id, symTable.getValue(id).toString());
            symTableList.add(e);
        }
        this.symbolTableView.setItems(FXCollections.observableList(symTableList));
        this.symbolTableView.refresh();
    }

    private void populateHeapTable()
    {
        if (this.controller.getRepo().getProgramsList().size() < 1)
            return;
        ProgramState programState = this.controller.getRepo().getProgramsList().get(0);
        IHeap<Integer, IValue> heap = programState.getHeap();
        List<Map.Entry<Integer, String>> heapList = new ArrayList<>();
        for (var address : heap.getUsedAddresses())
        {
            Map.Entry<Integer, String> e = new AbstractMap.SimpleEntry<>(address, heap.getValue(address).toString());
            heapList.add(e);
        }
        this.heapTableView.setItems(FXCollections.observableList(heapList));
        this.heapTableView.refresh();
    }

    private void populateFileTable()
    {
        if (this.controller.getRepo().getProgramsList().size() < 1)
            return;
        ProgramState programState = this.controller.getRepo().getProgramsList().get(0);
        IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
        List<String> fileTableList = fileTable.getKeys().stream().map(StringValue::toString).collect(Collectors.toList());
        this.fileTableListView.setItems(FXCollections.observableList(fileTableList));
        this.fileTableListView.refresh();
    }

    private void populateExecutionStack(ProgramState selectedProgramState)
    {

        IStack<IStatement> stack = selectedProgramState.getExecStack();
        List<String> stackList = stack.getAllString();
        this.execStackListView.setItems(FXCollections.observableList(stackList));
        this.execStackListView.refresh();
    }

    private void populateOutput()
    {
        if (this.controller.getRepo().getProgramsList().size() < 1)
            return;
        ProgramState programState = this.controller.getRepo().getProgramsList().get(0);
        IList<IValue> output = programState.getOutput();
        List<String> outputList = output.getAllString();
        this.outputListView.setItems(FXCollections.observableList(outputList));
        this.outputListView.refresh();
    }

}
