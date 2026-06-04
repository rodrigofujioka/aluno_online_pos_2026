package br.com.alunoonline.api.schedule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TarefasAgendadasTest {

    @Mock
    private Supplier<String> dummyDependency;

    @InjectMocks
    private TarefasAgendadas tarefasAgendadas;

    private String originalUserHome;
    private Path tempUserHome;

    @BeforeEach
    void setUp() throws IOException {
        originalUserHome = System.getProperty("user.home");
        tempUserHome = Files.createTempDirectory("tarefas-agendadas-test-");
        System.setProperty("user.home", tempUserHome.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        if (originalUserHome == null) {
            System.clearProperty("user.home");
        } else {
            System.setProperty("user.home", originalUserHome);
        }

        if (tempUserHome != null && Files.exists(tempUserHome)) {
            Files.walk(tempUserHome)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                            // Best effort cleanup for temporary files created in tests.
                        }
                    });
        }
    }

    @Test
    void shouldExecuteFixedRateTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa1_FixedRate());
    }

    @Test
    void shouldExecuteCronTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa2_CronExpression());
    }

    @Test
    void shouldExecuteFixedDelayTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa3_FixedDelay());
    }

    @Test
    void shouldExecuteMorningDailyTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefaDiaria_Manha());
    }

    @Test
    void shouldExecuteAfternoonDailyTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefaDiaria_Tarde());
    }

    @Test
    void shouldExecuteEveryFiveMinutesTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_A_Cada_5_Minutos());
    }

    @Test
    void shouldExecuteWeekdaysTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_DiaUteis());
    }

    @Test
    void shouldExecuteMidnightTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_MeiaNoite());
    }

    @Test
    void shouldExecuteFirstDayOfMonthTaskWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_PrimeiroDiaDoMes());
    }

    @Test
    void shouldExecuteTaskWithTryCatchWhenMethodIsCalled() {
        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_ComTratamento());
    }

    @Test
    void shouldRotateLogsAndDeleteOldLogsWhenDirectoryContainsExpiredFile() throws IOException {
        Path logsDir = tempUserHome.resolve("logs");
        Files.createDirectories(logsDir);

        Path oldLog = logsDir.resolve("application-old.log");
        Path recentLog = logsDir.resolve("application-recent.log");

        Files.writeString(oldLog, "old log");
        Files.writeString(recentLog, "recent log");

        long thirtyOneDaysMillis = 31L * 24 * 60 * 60 * 1000;
        oldLog.toFile().setLastModified(System.currentTimeMillis() - thirtyOneDaysMillis);

        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_RotacaoLogsAoAmanhecer());

        assertFalse(Files.exists(oldLog));
        assertTrue(Files.exists(recentLog));
    }

    @Test
    void shouldCreateLogsDirectoryWhenDirectoryDoesNotExist() {
        Path logsDir = tempUserHome.resolve("logs");
        assertFalse(Files.exists(logsDir));

        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_RotacaoLogsAoAmanhecer());

        assertTrue(Files.exists(logsDir));
    }

    @Test
    void shouldHandleExceptionWhenUserHomePathIsInvalid() {
        System.setProperty("user.home", "\u0000invalid-home");

        assertDoesNotThrow(() -> tarefasAgendadas.tarefa_RotacaoLogsAoAmanhecer());
        assertNotNull(dummyDependency);
    }
}

