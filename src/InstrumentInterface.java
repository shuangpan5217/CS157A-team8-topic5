import java.sql.SQLException;

public class InstrumentInterface {
	public static void main(String[] args) throws SQLException {
		Instrument vm = new Instrument();
		vm.setOwnReference(vm);
	}
}