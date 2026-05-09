package br.com.alunoonline.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CpfUtilsTest {

    @Test
    void deveRetornarNaoInformadoQuandoCpfForNulo() {
        assertEquals("não informado", CpfUtils.formatCpf(null));
    }

    @Test
    void deveMascararCpfMantendoSomenteOsUltimosQuatroDigitos() {
        assertEquals("***8990", CpfUtils.formatCpf("123.456.789-90"));
    }

    @Test
    void deveMascararCpfCurtoSemFalhar() {
        assertEquals("***123", CpfUtils.formatCpf("123"));
    }
}

