using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class PropDto : SerializeMessage {
	[ProtoMember(1)]
	public int exchangeCard;
	[ProtoMember(2)]
	public int kickingCard;
	[ProtoMember(3)]
	public int trumpetCard;
}
