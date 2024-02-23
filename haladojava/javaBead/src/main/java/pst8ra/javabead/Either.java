package pst8ra.javabead;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class Either<L, R> {
    private final L left;
    private final R right;

    private transient volatile int hashCode;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L left) {
        return new Either<>(left, null);
    }

    public static <L, R> Either<L, R> right(R right) {
        return new Either<>(null, right);
    }

    public static <R> R iterate(Either<R, R> either, int n, Function<R, R> f) {
        if (either.isLeft()) {
            return either.getLeft();
        } else {
            R result = either.getRight();
            for (int i = 0; i < n; i++) {
                try {
                    result = f.apply(result);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
            return result;
        }
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    public L getLeft() {
        if (left == null) {
            throw new NoSuchElementException("Left is null");
        }
        return left;
    }

    public R getRight() {
        if (right == null) {
            throw new NoSuchElementException("Right is null");
        }
        return right;
    }

    public Either<R, L> swap() {
        if (isLeft()) {
            return Either.right(getLeft());
        } else {
            return Either.left(getRight());
        }
    }

    public <T> Either<L, T> map(Function<R, T> f) {
        if (isLeft()) {
            return Either.left(getLeft());
        } else {
            try {
                return Either.right(f.apply(getRight()));
            } catch (Exception exc) {
                exc.printStackTrace();
                return null;
            }
        }
    }

    public <T> Either<L, T> bind(Function<R, Either<L, T>> f) {
        if (isLeft()) {
            return Either.left(getLeft());
        } else {
            try {
                return f.apply(getRight());
            } catch (Exception exc) {
                exc.printStackTrace();
                return null;
            }
        }
    }
}
