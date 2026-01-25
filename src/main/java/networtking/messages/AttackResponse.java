package networtking.messages;

import game.player.ShipType;

public record AttackResponse(ShipType shipType, AttackStatus attackStatus) {
}

enum AttackStatus {
    HIT, MISS, SUNK
}
