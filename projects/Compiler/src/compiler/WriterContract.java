package compiler;

public interface WriterContract {
    public void writePush(String label, int index) throws Exception;
    public void writePop(String label, int index) throws Exception;
    public void writeArithmetic(String operation) throws Exception;
    public void writeUnary(String operation) throws Exception;
    public void writeLabel(String label) throws Exception;
    public void writeGoto(String label) throws Exception;
    public void writeIf(String label) throws Exception;
    public void writeCall(String name, int nArgs) throws Exception;
    public void writeFunction(String name, int nLocals) throws Exception;
    public void writeReturn() throws Exception;
}
