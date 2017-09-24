package es.sandbox.snippet.pattern.visitor.enumeration;

/**
 * Created by jeslopalo on 22/9/17.
 */
public enum EnumType {

    FIRST {
        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitFirst();
        }
    },
    SECOND {
        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitSecond();
        }
    };

    abstract <T> T accept(Visitor<T> visitor);

    public interface Visitor<T> {

        T visitFirst();

        T visitSecond();

        default T visit(EnumType enumType) {
            return enumType.accept(this);
        }
    }
}
