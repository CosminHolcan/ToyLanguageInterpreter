package repository;

import model.MyException;
import model.programState.ProgramState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {

    List<ProgramState> getProgramsList();
    void logProgramStateExecution(ProgramState prg) throws MyException, IOException;
    void setProgramsList(List<ProgramState> prgState);
}
