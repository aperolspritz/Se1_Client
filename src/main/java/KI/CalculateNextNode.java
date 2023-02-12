package KI;

public class CalculateNextNode {
}

/*
 * private EMove move; private final static Logger logger = (Logger)
 * LoggerFactory.getLogger(CalculateNextNode.class);
 * 
 * public EMove getEMove() { return move; }
 * 
 * public CalculateNextNode() { };
 * 
 * public CalculateNextNode(FullMapNode currPos) {
 * 
 * if (currPos.getY() != FindTargetNode.targetNode.getY()) { if (currPos.getY()
 * > FindTargetNode.targetNode.getY()) { this.move = EMove.Up; } else {
 * this.move = EMove.Down; } } else { if (currPos.getX() >
 * FindTargetNode.targetNode.getX()) { this.move = EMove.Left; } else {
 * this.move = EMove.Right; } }
 * 
 * logger.info("Constructor Move: " + this.move.toString());
 * TargetNodeExceptionFunctionCall.targetNodeException(FindTargetNode.targetNode
 * );
 * 
 * }
 * 
 * private boolean isValidNode(FullMapNode node) { if (node.getTerrain() ==
 * ETerrain.Water) return false; return true; }
 * 
 * boolean checkNode(int x, int y, List<FullMapNode> map) { for (FullMapNode
 * node : map) { if (node.getX() == x && node.getY() == y && isValidNode(node))
 * { return true; } } return false;
 * 
 * }
 * 
 * //////////////////////////////////////////// private void
 * alternativeFieldUp(FullMapNode currPos, List<FullMapNode> map) { int x =
 * currPos.getX() - 1; int y = currPos.getY(); if (checkNode(x, y, map)) {
 * this.move = EMove.Left; return; }
 * 
 * if (checkNode(currPos.getX() + 1, currPos.getY(), map)) this.move =
 * EMove.Right; else this.move = EMove.Down;
 * 
 * }
 * 
 * private void checkNextFieldUp(int x, int y, List<FullMapNode> map,
 * FullMapNode currPos) { FullMapNode checkNode = new FullMapNode();
 * 
 * for (FullMapNode node : map) { if (node.getX() == x && node.getY() == y) {
 * checkNode = node; break; } }
 * 
 * try { MoveExceptionCall.checkNextField(checkNode); } catch (MoveExeption e) {
 * logger.warn("Warning in CalculateNextField" + e.getMessage() + " " +
 * e.getExceptionNode()); alternativeFieldUp(currPos, map); } }
 * 
 * private void alternativeFieldDown(FullMapNode currPos, List<FullMapNode> map)
 * { int x = currPos.getX() - 1; int y = currPos.getY();
 * 
 * if (checkNode(x, y, map)) { this.move = EMove.Left; return; }
 * 
 * if (checkNode(currPos.getX() + 1, currPos.getY(), map)) this.move =
 * EMove.Right; else this.move = EMove.Up; }
 * 
 * private void checkNextFieldDown(int x, int y, List<FullMapNode> map,
 * FullMapNode currPos) { FullMapNode checkNode = new FullMapNode();
 * 
 * for (FullMapNode node : map) { if (node.getX() == x && node.getY() == y) {
 * checkNode = node; break; } }
 * 
 * try { MoveExceptionCall.checkNextField(checkNode); } catch (MoveExeption e) {
 * logger.warn("Warning in CalculateNextField" + e.getMessage() + " " +
 * e.getExceptionNode()); alternativeFieldDown(currPos, map); } }
 * 
 * //////////////////////////////////////////
 * 
 * private void alternativeFieldLeft(FullMapNode currPos, List<FullMapNode> map)
 * { int x = currPos.getX(); int y = currPos.getY() + 1;
 * 
 * if (checkNode(x, y, map)) { this.move = EMove.Down; return;
 * 
 * }
 * 
 * if (checkNode(currPos.getX(), currPos.getY() - 1, map)) this.move = EMove.Up;
 * else this.move = EMove.Right; }
 * 
 * private void checkNextFieldLeft(int x, int y, List<FullMapNode> map,
 * FullMapNode currPos) { FullMapNode checkNode = new FullMapNode();
 * 
 * for (FullMapNode node : map) { if (node.getX() == x && node.getY() == y) {
 * checkNode = node; break; } }
 * 
 * try { MoveExceptionCall.checkNextField(checkNode); } catch (MoveExeption e) {
 * logger.warn("Warning in CalculateNextField" + e.getMessage() + " " +
 * e.getExceptionNode()); alternativeFieldLeft(currPos, map); } }
 * 
 * private void alternativeFieldRight(FullMapNode currPos, List<FullMapNode>
 * map) { int x = currPos.getX(); int y = currPos.getY() + 1;
 * 
 * if (checkNode(x, y, map)) { this.move = EMove.Down; return;
 * 
 * }
 * 
 * if (checkNode(currPos.getX(), currPos.getY() - 1, map)) this.move = EMove.Up;
 * else this.move = EMove.Left; }
 * 
 * private void checkNextFieldRight(int x, int y, List<FullMapNode> map,
 * FullMapNode currPos) { FullMapNode checkNode = new FullMapNode();
 * 
 * for (FullMapNode node : map) { if (node.getX() == x && node.getY() == y) {
 * checkNode = node; break; } }
 * 
 * try { MoveExceptionCall.checkNextField(checkNode); } catch (MoveExeption e) {
 * logger.warn("Warning in CalculateNextField" + e.getMessage() + " " +
 * e.getExceptionNode()); alternativeFieldRight(currPos, map); } }
 * 
 * public EMove calculateMove(List<FullMapNode> map, FullMapNode currPos, EMove
 * move) { if (this.move == null) this.move = move;
 * 
 * switch (this.move) { case Up: checkNextFieldUp(currPos.getX(), currPos.getY()
 * - 1, map, currPos);
 * 
 * break; case Down: checkNextFieldDown(currPos.getX(), currPos.getY() + 1, map,
 * currPos);
 * 
 * break; case Left: checkNextFieldRight(currPos.getX() - 1, currPos.getY(),
 * map, currPos);
 * 
 * break; case Right: checkNextFieldLeft(currPos.getX() + 1, currPos.getY(),
 * map, currPos);
 * 
 * break; } return this.move; }
 * 
 * }
 */

/////////////////////////////////////////////////
