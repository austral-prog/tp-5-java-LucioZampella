package com.cinema;

import java.util.ArrayList;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {

    private Seat[][] seats;

    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];
        }
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int availableSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] != null && seats[i][j].isAvailable()) {
                    availableSeats++;
                }
            }
        }

        return availableSeats;
    }
    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        if (row >= 0 && row < seats.length) {
            for (int j = 0; j < seats[row].length; j++) {
                if (seats[row][j].isAvailable()) {
                    return seats[row][j];
                }
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isAvailable()) {
                    return seats[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        if (row < 0 || row >= seats.length) {
            throw new IllegalArgumentException("Invalid row number");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid Amount");
        }

        int consecutiveAvailable = 0;

        for (int j = 0; j < seats[row].length; j++) {
            if (seats[row][j].isAvailable()) {
                consecutiveAvailable++;
                // Check if we have enough consecutive seats
                if (consecutiveAvailable == amount) {
                    return seats[row][j - amount + 1];
                }
            } else {
                consecutiveAvailable = 0; // Reset if seat is not available
            }
        }

        return null; // No enough consecutive seats found in the row
    }


    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        int consecutiveAvailable = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].isAvailable()) {
                    consecutiveAvailable++;
                } else {
                    consecutiveAvailable = 0;
                }
                if (consecutiveAvailable == amount) {
                    return seats[i][j - amount + 1];
                }
            }
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        boolean seatFound = false;
        int reservedSeats = 0;

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == seat) {
                    seatFound = true;
                    for (int k = j; k < seats[i].length && reservedSeats < amount; k++) {
                        if (seats[i][k].isAvailable()) {
                            seats[i][k].takeSeat();
                            reservedSeats++;
                        } else {
                            break;
                        }
                    }
                    if (reservedSeats < amount) {
                        for (int k = j; k < j + reservedSeats; k++) {
                            seats[i][k].releaseSeat();
                        }
                        throw new ArrayIndexOutOfBoundsException("Not enough seats available");
                    }
                    break;
                }
            }
            if (seatFound) {
                break;
            }
        }

        if (!seatFound) {
            throw new ArrayIndexOutOfBoundsException("Initial seat not found");
        }
    }



    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        boolean seatFound = false;
        int releasedSeats = 0;

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == seat) {
                    seatFound = true;
                    if (amount > seats[i].length - j) {
                        throw new ArrayIndexOutOfBoundsException("Invalid amount: trying to release more seats than available.");
                    }

                    for (int k = j; k < seats[i].length && releasedSeats < amount; k++) {
                        if (!seats[i][k].isAvailable()) {
                            seats[i][k].releaseSeat();
                            releasedSeats++;
                        } else {
                            break;
                        }
                    }
                    if (releasedSeats < amount) {
                        for (int k = j; k < j + releasedSeats; k++) {
                            seats[i][k].takeSeat();
                        }
                        releasedSeats = 0;
                    }

                    break;
                }
            }

            if (seatFound) {
                break;
            }
        }

        if (!seatFound) {
            throw new ArrayIndexOutOfBoundsException("Initial seat not found");
        }
    }

}
