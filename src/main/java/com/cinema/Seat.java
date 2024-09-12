package com.cinema;

/**
 * Clase que representa una butaca en el cine. Posee una fila y un lugar en la fila.
 * Se puede saber si está ocupada o no.
 */
public class Seat {

    private int row;
    private int seatNumber;
    private boolean available = true;

    /** Construye butacas, para ello se le debe pasar su ubicación, representada por
     * la fila y el número de asiento. */
    public Seat(int row, int seatNumber) {
        this.row = row;
        this.seatNumber = seatNumber;
            }
    /** Retorna el número de la fila. */
    public int getRow() {
        return row;
    }

    /** Retorna el número de asiento dentro de la fila. */
    public int getSeatNumber() {
        return seatNumber;
    }

    /** Retorna true si el asiento está disponible, o false en caso contrario. */
    public boolean isAvailable() {
        return available;
    }

    /** Ocupa el asiento. */
    public void takeSeat() {
        available = false;
    }

    /** Desocupa el asiento. */
    public void releaseSeat() {
        available = true;
    }
}

