package iotbay.filters;

import iotbay.models.entities.User;

class ProtectedPath {
    private String path;
    private boolean staffOnly;

    public ProtectedPath(String path, boolean staffOnly) {
        this.path = path;
        this.staffOnly = staffOnly;
    }

    public String getPath() {
        return path;
    }

    public boolean pathMatches(String path) {
        return path.startsWith(this.path);
    }

    public boolean isStaffOnly() {
        return staffOnly;
    }

    public boolean isUserAllowed(User user) {
        return !staffOnly || user.isStaff();
    }
}
