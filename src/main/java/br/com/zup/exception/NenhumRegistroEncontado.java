package br.com.zup.exception;

public class NenhumRegistroEncontado extends RuntimeException {

    public NenhumRegistroEncontado() {
    }

    public NenhumRegistroEncontado(String message) {
        super(message);
    }
}
