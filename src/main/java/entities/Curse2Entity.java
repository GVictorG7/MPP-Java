//package entities;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Table(name = "Curse2", schema = "main", catalog = "")
//public class Curse2Entity {
//    private Short id;
//    private Short cap;
//
//    @Id
//    @Column(name = "id")
//    public Short getId() {
//        return id;
//    }
//
//    public void setId(Short id) {
//        this.id = id;
//    }
//
//    @Basic
//    @Column(name = "cap")
//    public Short getCap() {
//        return cap;
//    }
//
//    public void setCap(Short cap) {
//        this.cap = cap;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Curse2Entity that = (Curse2Entity) o;
//        return Objects.equals(id, that.id) &&
//                Objects.equals(cap, that.cap);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(id, cap);
//    }
//}
