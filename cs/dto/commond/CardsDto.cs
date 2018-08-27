using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class CardsDto : SerializeMessage {
	[ProtoMember(1)]
	public int cardType;
	[ProtoMember(2)]
	public List<int> cardIds;
}
