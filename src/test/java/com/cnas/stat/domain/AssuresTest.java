package com.cnas.stat.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cnas.stat.web.rest.TestUtil;

public class AssuresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assures.class);
        Assures assures1 = new Assures();
        assures1.setId(1L);
        Assures assures2 = new Assures();
        assures2.setId(assures1.getId());
        assertThat(assures1).isEqualTo(assures2);
        assures2.setId(2L);
        assertThat(assures1).isNotEqualTo(assures2);
        assures1.setId(null);
        assertThat(assures1).isNotEqualTo(assures2);
    }
}
