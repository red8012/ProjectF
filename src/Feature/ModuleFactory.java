package Feature;
public abstract class ModuleFactory {
	final String NAME;

	public ModuleFactory(String name) {
		NAME = name;
	}

	abstract public Double calculate(String code, int row);
}
