package org.gcreator.runero;

public interface Preloadable {
    
    public boolean preload = false;
    
    public boolean isLoaded();
    public void load();
}
