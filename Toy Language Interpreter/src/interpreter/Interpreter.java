package interpreter;

import controller.Controller;
import model.adts.*;
import model.expressions.*;
import model.programState.ProgramState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntegerType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntegerValue;
import model.values.StringValue;
import repository.IRepository;
import repository.MyRepository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;

public class Interpreter {

    public static void main(String[] args) {

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

        TextMenu menu= new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        /*menu.addCommand(new RunExample("1", ex1.toString(), ex1, "log1.txt"));
        menu.addCommand(new RunExample("2", ex2.toString(), ex2, "log2.txt"));
        menu.addCommand(new RunExample("3", ex3.toString(), ex3, "log3.txt"));
        menu.addCommand(new RunExample("4", ex4.toString(), ex4, "log4.txt"));
        menu.addCommand(new RunExample("5", ex5.toString(), ex5, "log5.txt"));
        menu.addCommand(new RunExample("6", ex6.toString(), ex6, "log6.txt"));
        menu.addCommand(new RunExample("7", ex7.toString(), ex7, "log7.txt"));
        menu.addCommand(new RunExample("8", ex8.toString(), ex8, "log8.txt"));
        menu.addCommand(new RunExample("9", ex9.toString(), ex9, "log9.txt"));*/
        menu.addCommand(new RunExample("10", ex10.toString(), ex10, "log10.txt"));
        menu.addCommand(new RunExample("11", ex11.toString(), ex11, "log11.txt"));
        menu.addCommand(new RunExample("12", ex12.toString(), ex12, "log12.txt"));
        menu.addCommand(new RunExample("13", ex13.toString(), ex13, "log13.txt"));
        menu.addCommand(new RunExample("14", ex14.toString(), ex14, "log14.txt"));
        menu.addCommand(new RunExample("15", ex15.toString(), ex15, "log15.txt"));
        menu.addCommand(new RunExample("16", ex16.toString(), ex16, "log16.txt"));
        menu.run();

    }
}
