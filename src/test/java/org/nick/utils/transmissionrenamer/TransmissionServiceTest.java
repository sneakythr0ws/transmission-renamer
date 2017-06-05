package org.nick.utils.transmissionrenamer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransmissionServiceTest {

    @Autowired
    private TransmissionService transmissionService;

    @Test
    public void renameTest() throws IOException {
        transmissionService.rename();
    }
}
