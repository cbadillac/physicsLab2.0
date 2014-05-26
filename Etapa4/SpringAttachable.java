interface SpringAttachable {
  void attachSpring(Spring s);
  void detachSpring(Spring s);
  boolean isAttachedTo(Spring spring);
  double getPosition();
}
