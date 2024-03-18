import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceFile {
	LocalDateTime now;
	int hours;

	InvoiceFile(String fname, String lname, int hours, double basePrice, double cost, LocalDateTime now) {

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		this.now = now;
		this.hours = hours;

		LocalDateTime future = now.plusHours(hours);
		String now_2 = now.format(timeFormatter);
		String future_2 = future.format(timeFormatter);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("uow textfile.txt"));
			writer.write(fname + " ");
			writer.write(lname + " ");
			writer.write(hours + " ");
			writer.write(basePrice + " ");
			writer.write(cost + " ");
			writer.write(now_2 + " ");
			writer.write(future_2 + " ");
			writer.close();
		} catch (IOException ex) {

		}

	}

}
