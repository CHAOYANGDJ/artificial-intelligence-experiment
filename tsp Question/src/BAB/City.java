package BAB;
import java.awt.geom.Point2D;
public class City extends Point2D.Double {
	private String name;

	public City(String name, double x, double y) {
		super(x, y);
		this.name = name;
	}


	public String getName() {
		return name;
	}
}
