package sample;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.MyException;
import model.adts.*;
import model.expressions.*;
import model.programState.ProgramState;
import model.statements.*;
import model.types.*;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntegerValue;
import model.values.StringValue;

import javafx.event.ActionEvent;
import repository.IRepository;
import repository.MyRepository;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static javafx.application.Application.launch;

public class SelectProgramGUI implements Initializable {

    @FXML
    private Button runButton;

    @FXML
    private ListView<String> listViewPrograms;

    private List<IStatement> statements;
    private RunProgramGUI windowRun;

    public void setWindowRun(RunProgramGUI windowRun) {
        this.windowRun = windowRun;
    }

    private void createProgramStatements()
    {
        IStatement ex1= new CompoundStatement(new DeclarativeStatement("v",new IntegerType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        IStatement ex2 = new CompoundStatement( new DeclarativeStatement("a",new IntegerType()),
                new CompoundStatement(new DeclarativeStatement("b",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression
                                (1,new ValueExpression(new IntegerValue(2)),new
                                        ArithmeticExpression(3,new ValueExpression(new IntegerValue(3)),
                                        new ValueExpression(new IntegerValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression(1,
                                        new VariableExpression("a"), new ValueExpression(new IntegerValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        IStatement ex3 = new CompoundStatement(new DeclarativeStatement("a",new BoolType()),
                new CompoundStatement(new DeclarativeStatement("v", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        IStatement ex4 = new CompoundStatement(new DeclarativeStatement("fileName", new StringType()),
                new CompoundStatement(new DeclarativeStatement("varc", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("fileName", new ValueExpression(new StringValue("test1.txt"))),
                                new CompoundStatement(new OpenRFileStatement(new VariableExpression("fileName")),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("fileName"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("fileName"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("fileName"))) )))))));

        IStatement ex5 = new CompoundStatement(new DeclarativeStatement("a", new IntegerType()),
                new CompoundStatement(new DeclarativeStatement("b", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntegerValue(5))),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntegerValue(10))),
                                        new IfStatement( new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"),2),
                                                new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b")))))));

        IStatement ex6 = new CompoundStatement(new DeclarativeStatement("file", new StringType()),
                new CompoundStatement(new AssignmentStatement("file", new ValueExpression(new StringValue("inexisting.txt"))),
                        new CompoundStatement(new DeclarativeStatement("a", new IntegerType()),
                                new CompoundStatement(new OpenRFileStatement(new VariableExpression("file")),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("file"), "a"),
                                                new PrintStatement(new VariableExpression("a")))))));

        IStatement ex7 = new CompoundStatement(new DeclarativeStatement("var", new IntegerType()),
                new CompoundStatement(new DeclarativeStatement("file", new StringType()),
                        new CompoundStatement(new AssignmentStatement("file", new ValueExpression(new StringValue("test2.txt"))),
                                new CompoundStatement(new OpenRFileStatement(new VariableExpression("file")),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("file"), "var"),
                                                new CompoundStatement(new OpenRFileStatement(new VariableExpression("file")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("file"), "var"),
                                                                new PrintStatement(new VariableExpression("var")))))))));

        IStatement ex8 = new CompoundStatement(new DeclarativeStatement("file1", new StringType()), new CompoundStatement(new DeclarativeStatement("file2", new StringType()), new CompoundStatement(new DeclarativeStatement("a", new IntegerType()),
                new CompoundStatement(new AssignmentStatement("file1", new ValueExpression(new StringValue("test1.txt"))),
                        new CompoundStatement(new AssignmentStatement("file2", new ValueExpression(new StringValue("test2.txt"))),
                                new CompoundStatement(new OpenRFileStatement(new VariableExpression("file1")),
                                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("file2")),
                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("file1"), "a"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("file2"), "a"),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                                                new CompoundStatement(new CloseRFileStatement(new VariableExpression("file1")),
                                                                                        new CloseRFileStatement(new VariableExpression("file1"))))))))))))));


        IStatement ex9 = new CompoundStatement(new DeclarativeStatement("a", new IntegerType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntegerValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("a"), new ValueExpression(new IntegerValue(0)), 6),
                                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new AssignmentStatement("a", new ArithmeticExpression(2, new VariableExpression("a"), new ValueExpression(new IntegerValue(1)))))        ),
                                new PrintStatement(new VariableExpression("a")))));

        IStatement ex10 = new CompoundStatement(new DeclarativeStatement("a", new BoolType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(false))),
                        new CompoundStatement(new DeclarativeStatement("b", new IntegerType()),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntegerValue(17))),
                                        new CompoundStatement(new WhileStatement(new VariableExpression("a"), new PrintStatement(new VariableExpression("b"))),
                                                new PrintStatement(new VariableExpression("a")))))));

        IStatement ex11 = new CompoundStatement(new DeclarativeStatement("v",  new ReferenceType(new IntegerType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(new DeclarativeStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))))));

        IStatement ex12 = new CompoundStatement(new DeclarativeStatement("v", new ReferenceType(new IntegerType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntegerValue(17))),
                                        new PrintStatement(new ArithmeticExpression(1, new HeapReadExpression(new VariableExpression("v")), new ValueExpression(new IntegerValue(5))))))));

        IStatement ex13 = new CompoundStatement(new DeclarativeStatement("r", new ReferenceType(new IntegerType())),
                new CompoundStatement(new DeclarativeStatement("s", new StringType()),
                        new CompoundStatement(new AssignmentStatement("s", new ValueExpression(new StringValue("hello"))),
                                new CompoundStatement(new HeapAllocationStatement("r", new VariableExpression("s")),
                                        new PrintStatement(new HeapReadExpression(new VariableExpression("r")))))));

        IStatement ex14 = new CompoundStatement(new DeclarativeStatement("r", new ReferenceType(new IntegerType())),
                new CompoundStatement(new HeapAllocationStatement("r", new ValueExpression(new IntegerValue(5))),
                        new CompoundStatement(new DeclarativeStatement("v", new ReferenceType(new IntegerType())),
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))))));

        IStatement ex15 = new CompoundStatement(new DeclarativeStatement("a", new IntegerType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntegerValue(7))),
                        new WhileStatement(new VariableExpression("a"), new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                new AssignmentStatement("a", new ArithmeticExpression(2, new VariableExpression("a"), new ValueExpression(new IntegerValue(1))))))));

        IStatement ex16 = new CompoundStatement(new DeclarativeStatement("v", new IntegerType()),
                new CompoundStatement(new DeclarativeStatement("a", new ReferenceType(new IntegerType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(new ForkStatement(
                                                new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntegerValue(30))),
                                                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new ForkStatement(
                                                        new CompoundStatement(new DeclarativeStatement("s", new StringType()),
                                                                new CompoundStatement(new AssignmentStatement("s", new ValueExpression(new StringValue("here"))),
                                                                        new PrintStatement(new VariableExpression("s"))))),
                                                        new CompoundStatement( new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))))));

        IStatement ex17 = new CompoundStatement(new DeclarativeStatement("a", new IntegerType()),
                new CompoundStatement(
                        new ForkStatement(new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntegerValue(5))),
                                new PrintStatement(new VariableExpression("b")))),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntegerValue(17))),
                                new PrintStatement(new VariableExpression("a")))));

        this.statements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, ex13, ex14, ex15, ex16, ex17));

    }

    public void handlerButton()
    {
        int index = this.listViewPrograms.getSelectionModel().getSelectedIndex();

        if (index < 0)
            return;

        try {
            IStatement selectedStatement = this.statements.get(index);
            IDictionary<String, IType> dict = new MyDictionary<>();
            selectedStatement.typeCheck(dict);

            IStack<IStatement> stack = new MyStack<IStatement>();
            IDictionary<String , IValue> symbolsTable = new MyDictionary<String, IValue>();
            IList<IValue> output = new MyList<IValue>();
            IDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<StringValue, BufferedReader>();
            IHeap<Integer, IValue> heap = new MyHeap<Integer, IValue>();
            stack.push(selectedStatement);
            ProgramState prg = new ProgramState(stack, symbolsTable, output, fileTable, heap);
            IRepository repo = new MyRepository(prg, "log"+index+".txt");
            Controller controller = new Controller(repo);

            this.windowRun.setController(controller);
        }
        catch (MyException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "TC: "+e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createProgramStatements();
        this.listViewPrograms.setItems(FXCollections.observableArrayList(
                this.statements.stream().map(IStatement::toString).collect(Collectors.toList())
        ));

    }
}
