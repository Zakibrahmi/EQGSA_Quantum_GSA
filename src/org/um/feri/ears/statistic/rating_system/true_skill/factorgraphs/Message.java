package org.um.feri.ears.statistic.rating_system.true_skill.factorgraphs;

public class Message<T> {
	private final String nameFormat;
	private final Object[] nameFormatArgs;
	private T value;
	
	public Message() {
		this(null, null, (Object[])null);
	}
	
	public Message(T value, String nameFormat, Object... args) {
		
		this.nameFormat = nameFormat;
		nameFormatArgs = args;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return (nameFormat == null) ? super.toString() : String.format(nameFormat, nameFormatArgs);
	}
	
	@SuppressWarnings("all")
	public T getValue() {
		return this.value;
	}
	
	@SuppressWarnings("all")
	public void setValue(final T value) {
		this.value = value;
	}
}