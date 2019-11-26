

import java.sql.SQLException;


public interface InstrumentDataInterface {
	public static void main(String[] args) throws SQLException {
		InstrumentData vm = new InstrumentData();
		vm.setOwnReference(vm);
	}

}
