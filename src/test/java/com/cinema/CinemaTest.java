package com.cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaTest {

    private Cinema cinema;

    @BeforeEach
    public void setUp() {
        int[] rows = {5, 5, 5};
        cinema = new Cinema(rows);
    }

    @Test
    public void testCountAvailableSeats() {
        assertEquals(15, cinema.countAvailableSeats());
    }

    @Test
    public void testFindFirstAvailableSeatInRow() {
        Seat seat = cinema.findFirstAvailableSeatInRow(1);
        assertNotNull(seat);
        assertEquals(1, seat.getRow());
        assertEquals(0, seat.getSeatNumber());
    }

    @Test
    public void testFindFirstAvailableSeat() {
        Seat seat = cinema.findFirstAvailableSeat();
        assertNotNull(seat);
        assertEquals(0, seat.getRow());
        assertEquals(0, seat.getSeatNumber());
    }

    @Test
    public void testGetAvailableSeatsInRow() {
        Seat seat = cinema.getAvailableSeatsInRow(1, 3);
        assertNotNull(seat);
        assertEquals(1, seat.getRow());
        assertEquals(0, seat.getSeatNumber());
    }

    @Test
    public void testGetAvailableSeats() {
        Seat seat = cinema.getAvailableSeats(3);
        assertNotNull(seat);
        assertEquals(0, seat.getRow());
        assertEquals(0, seat.getSeatNumber());
    }

    @Test
    public void testTakeSeats() {
        Seat seat = cinema.findFirstAvailableSeat();
        cinema.takeSeats(seat, 3);
        assertEquals(12, cinema.countAvailableSeats());
    }

    @Test
    public void testReleaseSeats() {
        Seat seat = cinema.findFirstAvailableSeat();
        cinema.takeSeats(seat, 3);
        cinema.releaseSeats(seat, 3);
        assertEquals(15, cinema.countAvailableSeats());
    }
    @Test
    public void testFindFirstAvailableSeatInInvalidRow() {
        Seat seat = cinema.findFirstAvailableSeatInRow(10);
        assertNull(seat);
    }

    @Test
    public void testGetAvailableSeatsInRowWithMoreSeatsRequestedThanAvailable() {
        cinema.takeSeats(cinema.findFirstAvailableSeat(), 5);
        Seat seat = cinema.getAvailableSeatsInRow(0, 10);
        assertNull(seat);
    }

    @Test
    public void testGetAvailableSeatsWithMoreSeatsRequestedThanAvailable() {
        cinema.takeSeats(cinema.findFirstAvailableSeat(), 5);
        Seat seat = cinema.getAvailableSeats(20);
        assertNull(seat);
    }

    @Test
    public void testTakeSeatsWithInvalidAmount() {
        Seat seat = cinema.findFirstAvailableSeat();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            cinema.takeSeats(seat, 100); // Request more seats than available in a row
        });
    }

    @Test
    public void testReleaseSeatsWithInvalidAmount() {
        Seat seat = cinema.findFirstAvailableSeat();
        cinema.takeSeats(seat, 3);
        cinema.releaseSeats(seat, 4); // Request more seats to release than occupied
        assertEquals(12, cinema.countAvailableSeats()); // Should be back to original count
    }

    @Test
    public void testReleaseSeatsOutOfBounds() {
        Seat seat = cinema.findFirstAvailableSeat();
        cinema.takeSeats(seat, 2);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            cinema.releaseSeats(seat, 10); // Request to release more seats than taken
        });
    }

    @Test
    public void testTakeAndReleaseSeatsInSameRow() {
        Seat seat = cinema.findFirstAvailableSeat();
        cinema.takeSeats(seat, 3);
        cinema.releaseSeats(seat, 3);
        Seat newSeat = cinema.findFirstAvailableSeatInRow(seat.getRow());
        assertEquals(seat.getSeatNumber(), newSeat.getSeatNumber());
    }
}
