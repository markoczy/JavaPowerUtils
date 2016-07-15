package mkz.util.io.definition.trycatch;

@FunctionalInterface
public interface IIOTryProcessor
{
	public void process() throws Exception;
}
