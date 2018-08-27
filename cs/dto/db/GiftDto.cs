using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GiftDto : SerializeMessage {
	[ProtoMember(1)]
	public long flower;
	[ProtoMember(2)]
	public long egg;
	[ProtoMember(3)]
	public long bomb;
	[ProtoMember(4)]
	public int diamondRing;
	[ProtoMember(5)]
	public int sportsCar;
	[ProtoMember(6)]
	public int tower;
	[ProtoMember(7)]
	public int streamer;
	[ProtoMember(8)]
	public int aircraft;
	[ProtoMember(9)]
	public int blueSuccubus;
}
