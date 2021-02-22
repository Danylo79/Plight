package dev.dankom.event.type.interfaces;

public interface Cancellable {
    boolean isCancelled();
    void setCancelled(boolean cancelled);
}
