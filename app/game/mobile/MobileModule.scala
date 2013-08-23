package game.mobile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

import game.EventModule
import game.world.RoomModule

/**
 * Defines the behavior for all mobile entities (Players and NPCs)
 */
trait MobileModule extends EventModule {
  this: RoomModule ⇒

  case class Movement( val x: Int, val y: Int )
  case class Position( x: Int, y: Int ) {
    lazy val top = ( x, y + 2 )
    lazy val bottom = ( x, y - 2 )
    lazy val right = ( x + 1, y )
    lazy val left = ( x - 1, y )
  }

  private case object MoveBitch

  // Define events:
  case class MoveAttempt( p: Position, m: Movement ) extends Event

  trait Mobile {
    val name: String
    var position: Position
    var movement = Movement( 0, 0 )
  }

  /** An EventHandling Mobile object */
  trait EHMobile extends EventHandler with Mobile {

    val moveScheduler = system.scheduler.schedule( 0 millis, 1000 millis )( self ! MoveBitch )
    override def receive = ( { case MoveBitch ⇒ this emit MoveAttempt( position, movement ) }: Receive ) orElse super.receive

    protected def standing: Handle
    protected def moving: Handle

    protected def move( p: Position, m: Movement ) {
      this.position = Position( p.x + m.x, p.y + m.y )
      this.movement = m
    }

    private def startMoving( xdir: Int ) {
      this.handle = moving ~ default
      movement = Movement( xdir, movement.y )
    }

    protected def stopMoving {
      this.handle = standing ~ this.default
      this.movement = Movement( 0, movement.y )
    }

    protected def moveLeft = startMoving( -1 )
    protected def moveRight = startMoving( 1 )
    protected def jump = movement = Movement( movement.x, 5 )

  }
}