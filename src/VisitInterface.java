import java.sql.SQLException;

public class VisitInterface {
	public static void main(String[] args) throws SQLException {
		VisitMain vm = new VisitMain();
		vm.setOwnReference(vm);
	}
}