package vn.bookshare.common.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import vn.bookshare.common.base.Auditable;

public class AudiEntityListener {

    @PrePersist
    public void onCreate(Object entity) {
        if (entity instanceof Auditable auditable) {
            LocalDateTime now = LocalDateTime.now();
            auditable.setCreatedDate(now);
            auditable.setUpdatedDate(now);
        }
    }

    @PreUpdate
    public void onUpdate(Object entity) {
        if (entity instanceof Auditable auditable) {
            auditable.setUpdatedDate(LocalDateTime.now());
        }
    }
}
