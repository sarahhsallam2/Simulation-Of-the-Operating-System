
public class VarValue {
  private String variable;
  private Object value ;
  
  public VarValue(String variable,Object value) {
	  this.variable = variable;
	  this.value = value;
  }
  
public String getVariable() {
	return variable;
}
public void setVariable(String variable) {
	this.variable = variable;
}
public Object getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
  public String toString() {
	  return "Variable= " + variable + " Value= " + value;
  }
}
