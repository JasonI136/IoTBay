package iotbay.filters;

import iotbay.models.User;

/**
 * A class that represents a protected path
 */
class ProtectedPath {
    /**
     * The path
     */
    private final String path;

    /**
     * Whether the path can only be accessed by staff.
     */
    private final boolean staffOnly;

    /**
     * Creates a new protected path
     * @param path the path
     * @param staffOnly whether the path can only be accessed by staff
     */
    public ProtectedPath(String path, boolean staffOnly) {
        this.path = path;
        this.staffOnly = staffOnly;
    }

    /**
     * Gets the path
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Checks if the path matches the given path
     * @param path the path
     * @return whether the path matches the given path
     */
    public boolean pathMatches(String path) {
        return path.startsWith(this.path);
    }

    /**
     * Checks if the path can only be accessed by staff
     * @return whether the path can only be accessed by staff
     */
    public boolean isStaffOnly() {
        return staffOnly;
    }

    /**
     * Checks if the user is allowed to access the path
     * @param user the user
     * @return whether the user is allowed to access the path
     */
    public boolean isUserAllowed(User user) {
        return !staffOnly || user.isStaff();
    }
}
