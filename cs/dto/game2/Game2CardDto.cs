using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2CardDto : SerializeMessage {
	[ProtoMember(1)]
	public int position;
	[ProtoMember(2)]
	public int cardType;
	[ProtoMember(3)]
	public bool result;
	[ProtoMember(4)]
	public List<int> cardIds;
}
