package vn.bookshare.common.base;

import vn.bookshare.common.listener.AudiEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AudiEntityListener.class)
public abstract class Auditable {

    @Column(name = "created_date", updatable = false)
    protected LocalDateTime createdDate;
    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;
}
