package mkz.util.io.definition.sysout;

@FunctionalInterface
public interface IIOPrintFormatOverride
{
	public String format(int aLogLevel, String aClass, String aMethod, Integer aLineNumber);
}