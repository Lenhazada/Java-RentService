package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entites.CarRental;
import model.entites.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		IO.println("Entre com os dados do aluguel");
		IO.print("Modelo do carro: ");
		String carModel = sc.nextLine();
		IO.print("Retirada (dd/MM/yyyy hh:mm): ");
		LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
		IO.print("Retirada (dd/MM/yyyy hh:mm): ");
		LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);

		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

		IO.print("Entre com o preço por hora: ");
		double PricePerHour = sc.nextDouble();
		IO.print("Entre com o preço por dia: ");
		double PricePerDay = sc.nextDouble();

		RentalService rentalService = new RentalService(PricePerHour, PricePerDay, new BrazilTaxService());

		rentalService.processInvoice(cr);

		IO.println("FATURA:");
		// entra dentro da classe CarRental, passa pelo invoice e pega o basic payment
		IO.println("Pagamento básico: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		IO.println("Imposto: " + String.format("%.2f", cr.getInvoice().getTax()));
		IO.println("Pagamento total: : " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		sc.close();
	}

}
