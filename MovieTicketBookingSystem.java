import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Seat {
    private int seatNumber;
    private boolean isBooked;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }
}

class Movie {
    private String title;
    private List<Seat> seats;

    public Movie(String title, int numberOfSeats) {
        this.title = title;
        this.seats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void displaySeats() {
        System.out.println("\nAvailable Seats for " + title + "?");
        System.out.println("---------------------------------------------------");
        for (Seat seat : seats) {
            if (seat.isBooked()) {
                System.out.print("[X] "); 
            } else {
                System.out.printf("[%02d] ", seat.getSeatNumber()); 
            }
        }
        System.out.println("\n------------------------------------------------");
    }

    public boolean bookSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber && !seat.isBooked()) {
                seat.book();
                return true;
            }
        }
        return false;
    }
}

class Reservation {
    private Movie movie;
    private List<Seat> bookedSeats;

    public Reservation(Movie movie) {
        this.movie = movie;
        this.bookedSeats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        bookedSeats.add(seat);
    }

    public void displayReservation() {
        System.out.println("\nReservation Confirmed ");
        System.out.println("Movie: " + movie.getTitle());
        System.out.print("Seats: ");
        for (Seat seat : bookedSeats) {
            System.out.print("[" + seat.getSeatNumber() + "] ");
        }
        System.out.println("\n---------------------------------------------");
    }
}

public class MovieTicketBookingSystem {
    private List<Movie> movies;

    public MovieTicketBookingSystem() {
        movies = new ArrayList<>();
        movies.add(new Movie("Kalki", 10));
        movies.add(new Movie("Stree 2", 10));
        movies.add(new Movie("Shaitaan", 10));
    }

    public void displayMovies() {
        System.out.println("\nWelcome to the Movie Ticket Booking System");
        System.out.println("-----------------------------------------------");
        System.out.println("Available Movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i).getTitle());
        }
        System.out.println("-----------------------------------------------");
    }

    public Movie selectMovie(int movieIndex) {
        if (movieIndex >= 1 && movieIndex <= movies.size()) {
            return movies.get(movieIndex - 1);
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieTicketBookingSystem system = new MovieTicketBookingSystem();

        while (true) {
            system.displayMovies();
            System.out.print("\nSelect a movie (1-3) or 0 to exit: ");
            int movieIndex = scanner.nextInt();
            if (movieIndex == 0) {
                System.out.println("\nThank you for using the Movie Ticket Booking System! 🎥");
                break;
            }

            Movie selectedMovie = system.selectMovie(movieIndex);
            if (selectedMovie == null) {
                System.out.println("\nInvalid movie selection. Please try again.");
                continue;
            }

            selectedMovie.displaySeats();
            System.out.print("\nSelect a seat number to book or 0 to cancel: ");
            int seatNumber = scanner.nextInt();
            if (seatNumber == 0) {
                continue;
            }

            if (seatNumber < 1 || seatNumber > selectedMovie.getSeats().size()) {
                System.out.println("\nInvalid seat number. Please try again.");
                continue;
            }

            if (selectedMovie.bookSeat(seatNumber)) {
                Reservation reservation = new Reservation(selectedMovie);
                reservation.addSeat(selectedMovie.getSeats().get(seatNumber - 1));
                reservation.displayReservation();
            } else {
                System.out.println("\nSeat is already booked or invalid. Please try again.");
            }
        }

        scanner.close();
    }
}
