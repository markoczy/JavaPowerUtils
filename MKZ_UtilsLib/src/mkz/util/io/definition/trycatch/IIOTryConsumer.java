package mkz.util.io.definition.trycatch;

@FunctionalInterface
public interface IIOTryConsumer<T>
{
	public void process(T aValue) throws Exception;
}