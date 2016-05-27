package metodo.utils;

public class ElementoListaOperadoresCantidad {

	
	private EnumOperador enumOperador;
	private Long cantidad;
	
	
	public ElementoListaOperadoresCantidad(EnumOperador operador){
		this.enumOperador=operador;
		cantidad = 0L;
	}

	public EnumOperador getEnumOperador() {
		return enumOperador;
	}

	public void setEnumOperador(EnumOperador enumOperador) {
		this.enumOperador = enumOperador;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
	public void addRepeticiones(Long cantidad){
		this.cantidad+=cantidad;
	}
	
}
