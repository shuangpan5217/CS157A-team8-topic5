import java.sql.SQLException;

/**
 * The class will run the whole program.
 * @author shuangpan
 *
 */
public class VisitInterface {
	public static void main(String[] args) throws SQLException {
		VisitMain vm = new VisitMain();
		vm.setOwnReference(vm);
	}
} 