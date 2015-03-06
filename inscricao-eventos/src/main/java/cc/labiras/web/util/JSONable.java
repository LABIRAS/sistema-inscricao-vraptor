package cc.labiras.web.util;

import java.util.Map;

/**
 * Classes que implementam essa interface têm uma representação personalizada em JSON. Use {@link #json()} para
 * serializar este objeto.
 * 
 * @author Rafael Lins
 *
 */
public interface JSONable {
	/**
	 * @return Um dicionário de dados que representa este objeto e pode ser seguramente serializado em JSON.
	 */
	Map<String, Object> json();
}
